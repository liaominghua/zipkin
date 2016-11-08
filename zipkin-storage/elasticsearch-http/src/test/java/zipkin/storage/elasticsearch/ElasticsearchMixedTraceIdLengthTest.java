/**
 * Copyright 2015-2016 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.storage.elasticsearch;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import okhttp3.OkHttpClient;
import zipkin.storage.MixedTraceIdLengthTest;
import zipkin.storage.StorageComponent;
import zipkin.storage.elasticsearch.http.HttpClientBuilder;
import zipkin.storage.elasticsearch.http.HttpElasticsearchTestGraph;

public class ElasticsearchMixedTraceIdLengthTest extends MixedTraceIdLengthTest {

  private final ElasticsearchStorage storage;

  public ElasticsearchMixedTraceIdLengthTest() throws IOException {
    // verify all works ok
    HttpElasticsearchTestGraph.INSTANCE.storage.get().check();
    storage = ElasticsearchStorage.builder(
        HttpClientBuilder.create(new OkHttpClient())
            .flushOnWrites(true)
            .hosts(ImmutableList.of("http://localhost:9200")))
        .strictTraceId(false)
        .index("test_zipkin_http_mixed").build();
  }

  @Override protected StorageComponent storage() {
    return storage;
  }

  @Override public void clear() throws IOException {
    storage.clear();
  }
}
