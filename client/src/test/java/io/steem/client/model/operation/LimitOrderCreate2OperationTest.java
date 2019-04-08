package io.steem.client.model.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemExchangeRate;
import io.steem.client.model.SteemTime;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LimitOrderCreate2OperationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String owner = RandomStringUtils.randomAlphanumeric(8);
    long oderid = RandomUtils.nextLong();
    SteemAsset amountToSell = OperationTestUtils.randomSteemAsset();
    SteemExchangeRate exchangeRate = SteemExchangeRate.of(OperationTestUtils.randomSteemAsset(),
        OperationTestUtils.randomSteemAsset());
    boolean fillOrKill = RandomUtils.nextBoolean();
    SteemTime expiration = OperationTestUtils.randomSteemTime();

    LimitOrderCreate2Operation.Value value = LimitOrderCreate2Operation.Value.builder()
        .owner(owner)
        .oderid(oderid)
        .amountToSell(amountToSell)
        .exchangeRate(exchangeRate)
        .fillOrKill(fillOrKill)
        .expiration(expiration)
        .build();
    LimitOrderCreate2Operation sut = LimitOrderCreate2Operation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("owner", owner)
        .put("oderid", oderid)
        .put("amount_to_sell", ObjectMapUtils.toObjectMap(amountToSell))
        .put("exchange_rate", ObjectMapUtils.toObjectMap(exchangeRate))
        .put("fill_or_kill", fillOrKill)
        .put("expiration", OBJECT_MAPPER.convertValue(expiration, String.class))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "limit_order_create2", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("limit_order_create2", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
