package com.kylerdeggs.lambdatest;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SpringBootApplication
public class LambdaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LambdaTestApplication.class, args);
	}

	@Bean
	public Function<MessageDto, String> uppercase() {
		return value -> {
			AmazonSNS snsClient = AmazonSNSClient.builder().build();
			Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

			messageAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
					.withStringValue("Promotional")
					.withDataType("String"));

			return snsClient.publish(new PublishRequest()
					.withMessageAttributes(messageAttributes)
					.withMessage(value.getMessage())
					.withPhoneNumber(value.getPhoneNumber())).toString();
		};
	}

}
