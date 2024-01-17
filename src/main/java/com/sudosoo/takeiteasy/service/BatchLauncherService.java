package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

@Service
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchLauncherService {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final BatchJobService batchJobService;
    private final DataSource dataSource;
    private final PostService postService;

    Connection con = null;
    PreparedStatement pstmt = null ;

    public void runBatchJob() {
        String sql = "INSERT INTO post (title, content, category_id, member_id, view_count) VALUES (?, ?, ?, ?, ?)";

        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(sql);
            for (int i = 0; i < 100000; i++) {

                Post post = postService.createBatchPosts(i);

                pstmt.setString(1, post.getTitle());
                pstmt.setString(2, post.getContent());
                pstmt.setLong(3, 2L);
                pstmt.setLong(4, 2L);
                pstmt.setInt(5, post.getViewCount());

                // addBatch에 담기
                pstmt.addBatch();
                // 파라미터 Clear
                pstmt.clearParameters();

                if ((i % 10000) == 0) {
                    // Batch 실행
                    pstmt.executeBatch();
                    // Batch 초기화
                    pstmt.clearBatch();
                    // 커밋
                    con.commit();
                }
            }
            pstmt.executeBatch();
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (pstmt != null) try {
                pstmt.close();
                pstmt = null;
            } catch (SQLException ignored) {
            }
            if (con != null) try {
                con.close();
                con = null;
            } catch (SQLException ignored) {
            }
        }

    }
    @Transactional
    public void runBatchJobV2() throws Exception {

        // 1만개씩 10번에 걸쳐서 실행
        for (int i = 0; i < 10; i++) {
            int start = i * 10000;
            int end = (i + 1) * 10000;

            // Reader, Writer, Processor 생성
            ItemReader<Post> reader = batchJobService.reader(start, end);
            ItemWriter<Post> writer = batchJobService.writer(dataSource);

            // Step 생성
            Step step = batchJobService.createPostsStep(jobRepository, reader, writer, null);

            // Job 생성
            Job job = batchJobService.createPostsJob(jobRepository, step);

            // Job 실행
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder()
                    .addString("jobID", "createPostsJob" + i)
                    .addLong("timestamp", System.currentTimeMillis())
                    .addString("start", String.valueOf(start))
                    .addString("end", String.valueOf(end))
                    .toJobParameters());


            // Job 실행 결과 출력
            System.out.println("Job Status : " + jobExecution.getStatus());
            System.out.println("Job succeeded: " + jobExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED.getExitCode()));
        }
    }


}
