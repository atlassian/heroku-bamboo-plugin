This project is **DEPRECATED**!

The recommended alternatives are described in the [DevCenter article on WAR Deployment](https://devcenter.heroku.com/articles/war-deployment).

Heroku Bamboo Plugin
====================
Plugin for integrating with [Heroku](http://heroku.com) with [Atlassian Bamboo](http://www.atlassian.com/software/bamboo/overview).
Currently, the only build task available is WAR deployment, but expect more tasks to be introduced in the future.

Tasks
=====

WAR Deployment
--------------

This build task deploys a WAR file generated by your build directly to a Heroku app.

To deploy a WAR file, first make sure your build is successfully creating a deployable WAR file.
If you are using Maven with `<packaging>war</packaging>`, the `mvn package` command will output the WAR file into its `target` directory.
Otherwise, create the WAR file in whatever way is approiate for your build.

After the WAR file is created, add the `Heroku: Deploy WAR Artifact` task to your build configuration and specify:

 - API Key: Your Heroku API key to use for deployment. Your Heroku API key can be obtained from your [Heroku account page](https://api.heroku.com/account).
 - App Name: The app to which to deploy.
 - WAR File: Relative path to the WAR file created in a previous build step to deploy

Local Development
=================

Build & Run
-----------
- Install direct-to-heroku-client-java:
    - git clone git@github.com:heroku/direct-to-heroku-client-java.git
    - mvn install -DskipTests
- Download and install [Atlassian Plugin SDK](https://developer.atlassian.com/display/DOCS/Installing+the+Atlassian+Plugin+SDK)
- Run: `mvn bamboo:run` Note, if running with your own Maven, be sure to also specify the `settings.xml` in the SDK

Tests
-----
When running tests, be sure to provide system properties `heroku.apiKey` and `heroku.appName` for test API key and test app.
