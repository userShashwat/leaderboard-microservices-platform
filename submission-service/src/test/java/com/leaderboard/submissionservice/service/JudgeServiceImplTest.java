package com.leaderboard.submissionservice.service;
import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import com.leaderboard.submissionservice.dto.Result.JudgeResult;
import com.leaderboard.submissionservice.service.impl.JudgeServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class JudgeServiceImplTest {
    private final JudgeServiceImpl judgeService = new JudgeServiceImpl();

    @Test
    void shouldRejectEmptyCode() {

        JudgeResult result = judgeService.judge("");

        assertEquals(SubmissionStatus.REJECTED, result.getStatus());
        assertEquals(0, result.getScore());
    }

    @Test
    void shouldReturnScore300WhenForLoopExists() {

        String code = """
                public class Main{
                    public static void main(String[] args){
                        for(int i=0;i<10;i++){}
                    }
                }
                """;

        JudgeResult result = judgeService.judge(code);

        assertEquals(SubmissionStatus.ACCEPTED, result.getStatus());
        assertEquals(300, result.getScore());
    }

    @Test
    void shouldReturnScore200WhenWhileLoopExists() {

        String code = """
                public class Main{
                    public static void main(String[] args){
                        while(false){}
                    }
                }
                """;

        JudgeResult result = judgeService.judge(code);

        assertEquals(SubmissionStatus.ACCEPTED, result.getStatus());
        assertEquals(200, result.getScore());
    }
}
