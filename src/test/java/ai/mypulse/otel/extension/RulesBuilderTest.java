package ai.mypulse.otel.extension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class RulesBuilderTest {

  @Test
  void returnsEmptyIfStringIsNull() {
    List<ExclusionRule> rules = RulesBuilder.fromString(null);

    assertEquals(0, rules.size());
  }

  @Test
  void returnsEmptyIfStringIsEmpty() {
    List<ExclusionRule> rules = RulesBuilder.fromString("");

    assertEquals(0, rules.size());
  }

  @Test
  void returnsRuleForSinglePair() {
    List<ExclusionRule> rules = RulesBuilder.fromString("http.target:/something");

    assertEquals(1, rules.size());
    ExclusionRule rule = rules.get(0);
    assertEquals("http.target", rule.attributeKey.getKey());
    assertEquals("/something", rule.pattern.pattern());
  }

  @Test
  void returnsRuleForMultiplePair() {
    List<ExclusionRule> rules = RulesBuilder.fromString("http.target:/foo,http.target:/bar");

    assertEquals(2, rules.size());
    ExclusionRule rule1 = rules.get(0);
    ExclusionRule rule2 = rules.get(1);
    assertEquals("http.target", rule1.attributeKey.getKey());
    assertEquals("/foo", rule1.pattern.pattern());
    assertEquals("http.target", rule2.attributeKey.getKey());
    assertEquals("/bar", rule2.pattern.pattern());
  }
}
