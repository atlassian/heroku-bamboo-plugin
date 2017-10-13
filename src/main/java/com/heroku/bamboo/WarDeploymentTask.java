package com.heroku.bamboo;

import com.atlassian.bamboo.security.EncryptionService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;

public class WarDeploymentTask extends AbstractDeploymentTask<WarDeploymentTaskConfigurator> {
    @Inject
    public WarDeploymentTask(@ComponentImport EncryptionService encryptionService,
                             WarDeploymentTaskConfigurator warDeploymentTaskConfigurator) {
        super(encryptionService, warDeploymentTaskConfigurator);
    }
}