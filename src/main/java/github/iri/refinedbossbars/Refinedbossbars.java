package github.iri.refinedbossbars;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Refinedbossbars.MODID)
public class Refinedbossbars{
    public static final String MODID = "refinedbossbars";

    public Refinedbossbars(){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }
}
