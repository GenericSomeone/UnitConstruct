package construct.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;

import static mindustry.game.Objectives.*;

public class ConstructTechTree implements ContentList {
    static TechTree.TechNode context = null;

    @Override
    public void load(){

        /*tech tree soon
        extendNode(coreShard, () -> {

        });

        extendNode(Items.copper, () -> {

        });

        extendNode(groundZero,  () -> {
        });*/

    }

    //stolen node code
    //sets context to the node from the UnlockableContent
    private static void extendNode(UnlockableContent parent, Runnable children){
        TechNode parnode = TechTree.all.find(t -> t.content == parent);
        context = parnode;
        children.run();
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, boolean debug, Runnable children){
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, false, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, false, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.and(new Produce(content)), false, children);
    }

    private static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, Seq.with(), children);
    }

    private static void nodeProduce(UnlockableContent content){
        nodeProduce(content, Seq.with(), () -> {});
    }

    private static void debugNode(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, ItemStack.with(), objectives, true, children);
    }

    private static void debugNode(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, true, children);
    }

    private static void debugNode(UnlockableContent content, Runnable children){
        node(content, ItemStack.with(), null, true, children);
    }
}
