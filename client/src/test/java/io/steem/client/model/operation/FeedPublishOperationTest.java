package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemExchangeRate;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FeedPublishOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String publisher = RandomStringUtils.randomAlphanumeric(8);
    SteemExchangeRate exchangeRate = SteemExchangeRate.of(
        OperationTestUtils.randomSteemAsset(), OperationTestUtils.randomSteemAsset());

    FeedPublishOperation.Value value = FeedPublishOperation.Value.builder()
        .publisher(publisher)
        .exchangeRate(exchangeRate)
        .build();
    FeedPublishOperation sut = FeedPublishOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("publisher", publisher)
        .put("exchange_rate", ObjectMapUtils.toObjectMap(exchangeRate))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "feed_publish", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("feed_publish", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
