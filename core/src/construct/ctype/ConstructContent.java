//this seems important
package construct.ctype;

import arc.files.Fi;
import arc.util.Disposable;
import arc.util.Nullable;
import construct.core.ConstructContentLoader;
import mindustry.ctype.Content;
import mindustry.mod.Mods.LoadedMod;
import construct.Varsr;

@SuppressWarnings("uninitialized")

/** Base class for a content type that is loaded in {@link ConstructContentLoader}. */
public abstract class ConstructContent implements Comparable<Content>, Disposable{
    public final short id;
    /** Info on which mod this content was loaded from. */
    public ModContentInfo minfo = new ModContentInfo();

    public ConstructContent(){
        id = 0;
    }

    /** Called after all content and modules are created. Do not use to load regions or texture data! */
    public void init(){}

    /**
     * Called after all content is created, only on non-headless versions.
     * Use for loading regions or other image data.
     */
    public void load(){

    }

    /** @return whether an error occurred during mod loading. */
    public boolean hasErrored(){
        return minfo.error != null;
    }

    @Override
    public void dispose(){
        //does nothing by default
    }

    @Override
    public int compareTo(Content o) {
        return Integer.compare(id, o.id);
    }

    @Override
    public String toString(){
        return getContentType().name + "#" + id;
    }

    public ConstructContentType getContentType(){
        return Varsr.content.getContentType("unused");
    }

    public static class ModContentInfo{
        /** The mod that loaded this piece of content. */
        public @Nullable LoadedMod mod;
        /** File that this content was loaded from. */
        public @Nullable Fi sourceFile;
        /** The error that occurred during loading, if applicable. Null if no error occurred. */
        public @Nullable String error;
        /** Base throwable that caused the error. */
        public @Nullable Throwable baseError;
    }
}
