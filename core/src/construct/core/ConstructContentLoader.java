//stuff that I yanked from Endless Rusting

package construct.core;

import arc.func.Cons;
import arc.struct.Seq;
import mindustry.ctype.ContentList;
import construct.content.*;
import construct.ctype.*;

public class ConstructContentLoader {

    private final Seq<ContentList> contentLists = Seq.with(
            new ConstructStatusEffects(),
            new ConstructUnits(),
            new ConstructBlocks(),
            new ConstructTechTree()
    );

    public Seq<ConstructContentType> ContentTypes = Seq.with(
            new ConstructContentType("unused")
    );

    private Seq<MappableConstructContent>[] contentMap;

    public ConstructContentType getContentType(String name){
        return ContentTypes.find(c -> c.name == name);
    }

    public MappableConstructContent getByName(ConstructContentType content, String key){
        return null;
    }

    public <T extends ConstructContent> Seq<T> getBy(ConstructContentType type){
        return (Seq<T>)contentMap[type.ordinal];
    }

    public void load(){
        each(c -> {
            if (c instanceof UnlockableConstructContent) {
                UnlockableConstructContent content = (UnlockableConstructContent) c;
                content.load();
                content.loadIcon();
            }
        });
    }

    public void createContent(){
        contentMap = new Seq[ContentTypes.size];
        for (int i = 0; i < contentMap.length; i++) {
            contentMap[i] = new Seq();
        }
        contentLists.each(ContentList::load);
    }

    public void init(){
        each(c -> {
            if (c instanceof UnlockableConstructContent) {
                UnlockableConstructContent content = (UnlockableConstructContent) c;
                content.init();
            }
        });
    }

    public void each(Cons c){
        for (int i = 0; i < contentMap.length; i++) {
            contentMap[i].each(content -> {
                c.get(content);
            });
        }
    }


    public void handleContent(MappableConstructContent content){
        if(contentMap[content.getContentType().ordinal] == null) contentMap[content.getContentType().ordinal] = new Seq();
        contentMap[content.getContentType().ordinal].add(content);
    }

}
