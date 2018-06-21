package com.example.leeroyweb;

import org.springframework.cloud.aws.context.annotation.ConditionalOnAwsCloudEnvironment;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnAwsCloudEnvironment
@EnableContextInstanceData
public class AwsCustomization {

}
