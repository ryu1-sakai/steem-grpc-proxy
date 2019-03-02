package io.steem.client.model.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SbdExchangeRate;
import io.steem.client.model.SteemCurrencySbd;
import io.steem.client.model.SteemCurrencySteem;
import io.steem.client.model.SteemProperties;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WitnessSetPropertiesOperationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String owner = RandomStringUtils.randomAlphanumeric(8);
    SteemCurrencySteem accountCreationFee = SteemCurrencySteem.of(RandomUtils.nextFloat());
    long accountSubsidyBudget = RandomUtils.nextLong();
    long accountSubsidyDecay = RandomUtils.nextLong();
    int maximumBlockSize = RandomUtils.nextInt();
    SteemCurrencySteem sbdInterestRate = SteemCurrencySteem.of(RandomUtils.nextFloat());
    SbdExchangeRate sbdExchangeRate = SbdExchangeRate.of(
        SteemCurrencySbd.of(RandomUtils.nextFloat()),
        SteemCurrencySteem.of(RandomUtils.nextFloat()));
    String url = RandomStringUtils.randomAlphanumeric(8);
    String newSigningKey = RandomStringUtils.randomAlphanumeric(32);
    SteemProperties props = SteemProperties.builder()
        .accountCreationFee(accountCreationFee)
        .accountSubsidyBudget(accountSubsidyBudget)
        .accountSubsidyDecay(accountSubsidyDecay)
        .maximumBlockSize(maximumBlockSize)
        .sbdInterestRate(sbdInterestRate)
        .sbdExchangeRate(sbdExchangeRate)
        .url(url)
        .newSigningKey(newSigningKey)
        .build();
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    WitnessSetPropertiesOperation.Value value = WitnessSetPropertiesOperation.Value.builder()
        .owner(owner)
        .props(props)
        .extensions(extensions)
        .build();
    WitnessSetPropertiesOperation sut = WitnessSetPropertiesOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedProps = ImmutableMap.<String, Object>builder()
        .put("account_creation_fee", OBJECT_MAPPER.convertValue(accountCreationFee, String.class))
        .put("account_subsidy_budget", accountSubsidyBudget)
        .put("account_subsidy_decay", accountSubsidyDecay)
        .put("maximum_block_size", maximumBlockSize)
        .put("sbd_interest_rate", OBJECT_MAPPER.convertValue(sbdInterestRate, String.class))
        .put("sbd_exchange_rate", ObjectMapUtils.toObjectMap(sbdExchangeRate))
        .put("url", url)
        .put("new_signing_key", newSigningKey)
        .build();
    @SuppressWarnings("UnstableApiUsage")
    List<Map<String, Object>> extensionMaps = extensions.stream()
        .map(ObjectMapUtils::toObjectMap)
        .collect(ImmutableList.toImmutableList());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("owner", owner)
        .put("props", expectedProps)
        .put("extensions", extensionMaps)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "witness_set_properties", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("witness_set_properties", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
