package com.fortressofnerditude.createbroadcast;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue WALKIE_RANGE = BUILDER
            .comment("The range standard Walkie Talkies send their broadcasts. -1 for infinite")
            .defineInRange("walkieRange", 100, -1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue BRASS_WALKIE_RANGE = BUILDER
            .comment("The range brass Walkie Talkies send their broadcasts. -1 for infinite")
            .defineInRange("brassWalkieRange", 300, -1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue ENDER_WALKIE_RANGE = BUILDER
            .comment("The range ender Walkie Talkies send their broadcasts. -1 for infinite")
            .defineInRange("brassWalkieRange", -1, -1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
