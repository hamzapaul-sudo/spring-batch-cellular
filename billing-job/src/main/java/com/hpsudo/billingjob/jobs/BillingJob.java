package com.hpsudo.billingjob.jobs;

import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;

public class BillingJob implements Job {

    private final JobRepository jobRepository;

    public BillingJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public String getName() {
        return "BillingJob";
    }

    @Override
    public void execute(JobExecution execution) {
        try {
            JobParameters jobParameters = execution.getJobParameters();
            String inputFile = jobParameters.getString("input.file");
            System.out.println("Processing Information from input file " + inputFile);

            execution.setStatus(BatchStatus.COMPLETED);
            execution.setExitStatus(ExitStatus.COMPLETED);
        } catch (Exception e) {
            execution.addFailureException(e);
            execution.setStatus(BatchStatus.COMPLETED);
            execution.setExitStatus(ExitStatus.FAILED.addExitDescription(e.getMessage()));
        } finally {
            jobRepository.update(execution);
        }
    }
}
