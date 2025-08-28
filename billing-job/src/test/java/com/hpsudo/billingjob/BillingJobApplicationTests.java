package com.hpsudo.billingjob;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class BillingJobApplicationTests {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Test
    void testJobExecution(CapturedOutput output) throws Exception {

        //given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("input.file", "/some/input/file")
                .addString("file.format", "csv", false)
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        // assert
        assertTrue(output.getOut().contains("Processing Information from input file /some/input/file"));
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

}
