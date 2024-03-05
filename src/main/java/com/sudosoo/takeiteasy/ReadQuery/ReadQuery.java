package com.sudosoo.takeiteasy.ReadQuery;

import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadQuery {
    private final PostRepository postRepository;


}
