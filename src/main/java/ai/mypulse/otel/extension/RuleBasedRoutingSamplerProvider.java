/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package ai.mypulse.otel.extension;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSamplerProvider;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.util.List;

@AutoService(ConfigurableSamplerProvider.class)
public class RuleBasedRoutingSamplerProvider implements ConfigurableSamplerProvider {

  @Override
  public Sampler createSampler(ConfigProperties config) {
    String rawRules = System.getenv("AI_MYPULSE_OTEL_EXTENSION_EXCLUSION_RULES");
    List<ExclusionRule> rules = RulesBuilder.fromString(rawRules);
    return new RuleBasedRoutingSampler(rules);
  }

  @Override
  public String getName() {
    return "RuleBasedRoutingSampler";
  }
}
