package io.steem.client.model.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.SteemClientException;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemOperation;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
public class AccountCreateOperation extends SteemOperation {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final SteemAsset fee;
  private final String creator;
  private final String newAccountName;
  private final SteemAuthority owner;
  private final SteemAuthority active;
  private final SteemAuthority posting;
  private final String memoKey;
  private final String jsonMetadata;

  private AccountCreateOperation(SteemAsset fee, String creator, String newAccountName,
                                 SteemAuthority owner, SteemAuthority active,
                                 SteemAuthority posting, String memoKey, String jsonMetadata) {
    super("account_create");
    this.fee = fee;
    this.creator = creator;
    this.newAccountName = newAccountName;
    this.owner = owner;
    this.active = active;
    this.posting = posting;
    this.memoKey = memoKey;
    this.jsonMetadata = jsonMetadata;
  }

  @Override
  protected Map<String, Object> getValueMap() {
    try {
      return ImmutableMap.<String, Object>builder()
          .put("fee", OBJECT_MAPPER.writeValueAsString(fee))
          .put("creator", creator)
          .put("new_account_name", newAccountName)
          .put("owner", owner.compose())
          .put("active", active.compose())
          .put("posting", posting.compose())
          .put("memo_key", memoKey)
          .put("json_metadata", jsonMetadata)
          .build();
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Failed to compose: " + this, e);
    }
  }
}
