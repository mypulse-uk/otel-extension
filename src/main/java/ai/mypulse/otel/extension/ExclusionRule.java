/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copied from: https://github.com/open-telemetry/opentelemetry-java-contrib/blob/e1a86db303562a97e018f473ad3e962fb1a2b2e6/samplers/src/main/java/io/opentelemetry/contrib/sampler/SamplingRule.java
 */
package ai.mypulse.otel.extension;

import io.opentelemetry.api.common.AttributeKey;
import java.util.Objects;
import java.util.regex.Pattern;

class ExclusionRule {
  final AttributeKey<String> attributeKey;
  final Pattern pattern;

  ExclusionRule(AttributeKey<String> attributeKey, String pattern) {
    this.attributeKey = attributeKey;
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  public String toString() {
    return "SamplingRule{" + "attributeKey=" + attributeKey + ", pattern=" + pattern + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ExclusionRule)) {
      return false;
    }
    ExclusionRule that = (ExclusionRule) o;
    return attributeKey.equals(that.attributeKey) && pattern.equals(that.pattern);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributeKey, pattern);
  }
}
