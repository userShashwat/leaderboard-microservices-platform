package com.leaderboard.submissionservice.service;
import com.leaderboard.submissionservice.domain.*;
import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import com.leaderboard.submissionservice.dto.Request.CreateSubmissionRequest;
import com.leaderboard.submissionservice.dto.Response.SubmissionResponse;
import com.leaderboard.submissionservice.dto.Result.JudgeResult;
import com.leaderboard.submissionservice.kafka.SubmissionEventProducer;
import com.leaderboard.submissionservice.repository.ProblemRepository;
import com.leaderboard.submissionservice.repository.SubmissionRepository;
import com.leaderboard.submissionservice.service.JudgeService;
import com.leaderboard.submissionservice.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubmissionServiceImplTest {
    @Mock
    SubmissionRepository submissionRepository;

    @Mock
    JudgeService judgeService;

    @Mock
    SubmissionEventProducer submissionEventProducer;

    @Mock
    ProblemRepository problemRepository;

    @InjectMocks
    SubmissionServiceImpl submissionService;

    @Test
    void shouldSubmitSolutionSuccessfully() {

        Contest contest = Contest.builder()
                .id(1L)
                .build();

        Problem problem = Problem.builder()
                .id(1L)
                .contest(contest)
                .build();

        CreateSubmissionRequest request = CreateSubmissionRequest.builder()
                .code("public class Main{}")
                .build();

        JudgeResult result = JudgeResult.builder()
                .status(SubmissionStatus.ACCEPTED)
                .score(100)
                .build();

        Submission submission = Submission.builder()
                .id(1L)
                .problem(problem)
                .userId(10L)
                .username("adrita")
                .status(SubmissionStatus.ACCEPTED)
                .score(100)
                .build();

        when(problemRepository.findById(1L))
                .thenReturn(Optional.of(problem));

        when(judgeService.judge(anyString()))
                .thenReturn(result);

        when(submissionRepository.save(any()))
                .thenReturn(submission);

        SubmissionResponse response =
                submissionService.submitSolution(
                        1L,
                        10L,
                        "adrita",
                        request
                );

        assertNotNull(response);
        assertEquals(100, response.getScore());

        verify(submissionEventProducer).publish(any());
    }

    @Test
    void shouldThrowExceptionWhenProblemNotFound() {

        when(problemRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> submissionService.submitSolution(
                        1L,
                        1L,
                        "adrita",
                        CreateSubmissionRequest.builder().code("abc").build()
                )
        );
    }

    @Test
    void shouldPublishKafkaEvent() {

        Contest contest = Contest.builder().id(1L).build();

        Problem problem = Problem.builder()
                .id(1L)
                .contest(contest)
                .build();

        when(problemRepository.findById(1L))
                .thenReturn(Optional.of(problem));

        when(judgeService.judge(any()))
                .thenReturn(
                        JudgeResult.builder()
                                .status(SubmissionStatus.ACCEPTED)
                                .score(100)
                                .build()
                );

        when(submissionRepository.save(any()))
                .thenAnswer(invocation -> {

                    Submission s = invocation.getArgument(0);
                    s.setId(5L);
                    return s;

                });

        submissionService.submitSolution(
                1L,
                1L,
                "adrita",
                CreateSubmissionRequest.builder()
                        .code("public class Main{}")
                        .build()
        );

        verify(submissionEventProducer, times(1))
                .publish(any(ScoreEvent.class));
    }
}
