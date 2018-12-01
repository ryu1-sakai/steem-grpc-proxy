package io.steem.client;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpMessage;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class SteemClient {

    private static final Logger logger = LoggerFactory.getLogger(SteemClient.class);

    private final HttpClient httpClient;

    private SteemClient(URI uri) {
        httpClient = HttpClient.of(uri);
    }

    public static SteemClient of(URI uri) {
        return new SteemClient(uri);
    }

    public String call(String request) {
        AggregatedHttpMessage httpRequest = AggregatedHttpMessage.of(
                HttpMethod.POST, "/", MediaType.JSON_UTF_8, request);
        AggregatedHttpMessage response
                = httpClient.execute(httpRequest).aggregate().join();
        if (response.status() != HttpStatus.OK) {
            String errorMessage
                    = String.format("Error in calling Steem API: request<%s> response<%s>",
                    request, response);
            logger.warn(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return response.content().toStringUtf8();
    }

    private AggregatedHttpMessage call(AggregatedHttpMessage httpRequest) {
        try {
            return httpClient.execute(httpRequest).aggregate().join();
        } catch (Exception e) {
            logger.warn("Exception in calling Steem API: request<{}>", httpRequest, e);
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        SteemClient client = SteemClient.of(new URI("https://testnet.steemitdev.com"));
        String request = "{\"jsonrpc\":\"2.0\", \"method\":\"jsonrpc.get_methods\", \"id\":1}";
        System.out.println(client.call(request));
    }
}
