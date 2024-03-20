package com.sudosoo.takeItEasy.domain.ReadQuery;

import com.sudosoo.takeItEasy.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadQuery {
    private final PostRepository postRepository;

}
