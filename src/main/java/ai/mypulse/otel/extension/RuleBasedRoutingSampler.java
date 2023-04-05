/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copied from https://github.com/open-telemetry/opentelemetry-java-contrib/blob/e1a86db303562a97e018f473ad3e962fb1a2b2e6/samplers/src/main/java/io/opentelemetry/contrib/sampler/RuleBasedRoutingSampler.java#L46
 */

package ai.mypulse.otel.extension;

import static java.util.Objects.requireNonNull;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingDecision;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;
import java.util.List;

public final class RuleBasedRoutingSampler implements Sampler {
  private final List<ExclusionRule> rules;

  RuleBasedRoutingSampler(List<ExclusionRule> rules) {
    this.rules = requireNonNull(rules);
  }

  @Override
  public SamplingResult shouldSample(
      Context parentContext,
      String traceId,
      String name,
      SpanKind spanKind,
      Attributes attributes,
      List<LinkData> parentLinks) {
    for (ExclusionRule exclusionRule : rules) {
      String attributeValue = attributes.get(exclusionRule.attributeKey);
      if (attributeValue == null) {
        continue;
      }
      if (exclusionRule.pattern.matcher(attributeValue).find()) {
        return SamplingResult.create(SamplingDecision.DROP);
      }
    }
    return SamplingResult.create(SamplingDecision.RECORD_AND_SAMPLE);
  }

  @Override
  public String getDescription() {
    return "RuleBasedRoutingSampler{" + "rules=" + rules + '}';
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
