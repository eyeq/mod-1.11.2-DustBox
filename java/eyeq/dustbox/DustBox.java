package eyeq.dustbox;

import eyeq.dustbox.block.BlockDustBox;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.dustbox.DustBox.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class DustBox {
    public static final String MOD_ID = "eyeq_dustbox";

    @Mod.Instance(MOD_ID)
    public static DustBox instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Block dustBox;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        dustBox = new BlockDustBox(Material.GROUND).setHardness(0.2F).setResistance(53.0F).setUnlocalizedName("boxDust").setCreativeTab(CreativeTabs.DECORATIONS);

        GameRegistry.register(dustBox, resource.createResourceLocation("dust_box"));
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        GameRegistry.register(new ItemBlock(dustBox), dustBox.getRegistryName());
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dustBox), "ISI", "SES", "ISI",
                'E', Items.ENDER_EYE, 'S', new ItemStack(Blocks.STONEBRICK), 'I', UOreDictionary.OREDICT_IRON_INGOT));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(dustBox);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-DustBox");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, dustBox, "DustBox");
        language.register(LanguageResourceManager.JA_JP, dustBox, "ゴミ箱");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createBlockstateJson(project, dustBox, "blocks/endframe_top");
    }
}
