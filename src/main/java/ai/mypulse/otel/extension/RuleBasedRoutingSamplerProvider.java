/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package ai.mypulse.otel.extension;

import com.google.auto.service.AutoService;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSamplerProvider;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.util.ArrayList;
import java.util.List;

@AutoService(ConfigurableSamplerProvider.class)
public class RuleBasedRoutingSamplerProvider implements ConfigurableSamplerProvider {

  private static AttributeKey<String> HTTP_TARGET = AttributeKey.stringKey("http.target");

  @Override
  public Sampler createSampler(ConfigProperties config) {
    List<ExclusionRule> rules = new ArrayList<>();
    rules.add(new ExclusionRule(HTTP_TARGET, "/ping"));
    rules.add(new ExclusionRule(HTTP_TARGET, "/metrics"));
    rules.add(new ExclusionRule(HTTP_TARGET, "/health"));
    return new RuleBasedRoutingSampler(rules);
  }

  @Override
  public String getName() {
    return "RuleBasedRoutingSampler";
  }
}
