package com.heroku.bamboo;

import com.atlassian.bamboo.security.EncryptionService;
import com.atlassian.bamboo.task.TaskConfiguratorHelper;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class WarDeploymentTaskConfigurator extends AbstractDeploymentTaskConfigurator {
    @Inject
    public WarDeploymentTaskConfigurator(@ComponentImport EncryptionService encryptionService,
                                         @ComponentImport TaskConfiguratorHelper taskConfiguratorHelper) {
        super(encryptionService, taskConfiguratorHelper);
    }

    @Override
    public String getPipelineName() {
        return "war";
    }

    @Override
    public List<String> getRequiredFiles() {
        return ImmutableList.of("war");
    }
}
