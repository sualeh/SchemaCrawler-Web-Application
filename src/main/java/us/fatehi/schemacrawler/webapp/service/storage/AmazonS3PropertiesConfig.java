package us.fatehi.schemacrawler.webapp.service.storage;


import javax.validation.constraints.NotNull;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AmazonS3PropertiesConfig
{

  @Value("${AWS_ACCESS_KEY_ID:bad-access-key}")
  @NotNull
  private String awsKeyId;

  @Value("${AWS_SECRET:bad-secret}")
  @NotNull
  private String awsKeySecret;

  @Value("${AWS_REGION:us-east-1}")
  @NotNull
  private String awsRegion;

  @Value("${AWS_S3_BUCKET:sc-web-app-1}")
  @NotNull
  private String awsS3Bucket;

  @Bean(name = "awsRegion")
  @NotNull
  public Region awsRegion()
  {
    if (StringUtils.isBlank(awsRegion))
    {
      throw new RuntimeException("No AWS region provided");
    }
    return Region.getRegion(Regions.fromName(awsRegion));
  }

  @Bean(name = "awsCredentials")
  @NotNull
  public AWSCredentialsProvider awsCredentials()
  {
    if (StringUtils.isAnyBlank(awsKeyId, awsKeySecret))
    {
      throw new RuntimeException("No AWS credentials provided");
    }
    final AWSCredentials awsCredentials =
      new BasicAWSCredentials(awsKeyId, awsKeySecret);
    return new AWSStaticCredentialsProvider(awsCredentials);
  }

  @Bean(name = "awsS3Bucket")
  public String awsS3Bucket()
  {
    return awsS3Bucket;
  }

}
