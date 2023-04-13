package ai.mypulse.otel.extension;

import io.opentelemetry.api.common.AttributeKey;
import java.util.ArrayList;
import java.util.List;

public class RulesBuilder {
  public static List<ExclusionRule> fromString(String rawRules) {
    if (rawRules == null || rawRules.equals("")) return new ArrayList<>();

    ArrayList<ExclusionRule> rules = new ArrayList<>();
    for (String rawRule : rawRules.split(",")) {
      String[] split = rawRule.split(":");
      ExclusionRule rule = new ExclusionRule(AttributeKey.stringKey(split[0]), split[1]);
      rules.add(rule);
    }
    return rules;
  }
}
