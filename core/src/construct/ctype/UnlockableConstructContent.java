//this seems important
package construct.ctype;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.Nullable;
import mindustry.Vars;

public abstract class UnlockableConstructContent extends MappableConstructContent {

    /** Localized, formal name. Never null. Set to internal name if not found in bundle. */
    public String localizedName;
    /** Localized description & details. May be null. */
    public @Nullable
    String description, details;
    /** Whether this content is always unlocked. */
    public boolean alwaysUnlocked = false;
    /** Whether to show the description in the research dialog preview. */
    public boolean inlineDescription = true;
    /** Special logic icon ID. */
    public int iconId = 0;
    /** Icon of the content to use in UI. */
    public TextureRegion uiIcon;
    /** Icon of the full content. Unscaled.*/
    public TextureRegion fullIcon;
    /** Unlock state. Loaded from settings. Do not modify outside of the constructor. */
    protected boolean unlocked;

    public UnlockableConstructContent(String name){
        super(name);
        this.localizedName = Core.bundle.get(getContentType() + "." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull(getContentType() + "." + this.name + ".description");
        this.details = Core.bundle.getOrNull(getContentType() + "." + this.name + ".details");
        this.unlocked = Core.settings != null && Core.settings.getBool(this.name + "-unlocked", alwaysUnlocked);
    }


    public void loadIcon(){
        fullIcon =
            Core.atlas.find(getContentType().name() + "-" + name + "-full",
                Core.atlas.find(name + "-full",
                    Core.atlas.find(name,
                        Core.atlas.find(getContentType().name() + "-" + name,
                            Core.atlas.find(name + "1")))));

        uiIcon = Core.atlas.find(getContentType().name() + "-" + name + "-ui", fullIcon);
    }

    //unlock the piece of content
    public void unlock(){
        if(!unlocked){
            Core.settings.put(this.name + "-unlocked", true);
            unlocked = true;
        }
    }

    /** @return whether this content is unlocked, or the player is in a custom (non-campaign) game. */
    public boolean unlockedNow(){
        return unlocked() || !Vars.state.isCampaign();
    }

    public boolean locked(){
        return !unlocked();
    }

    public boolean unlocked(){
        if(Vars.net != null && Vars.net.client()) return alwaysUnlocked || Vars.state.rules.researched.contains(name);
        return unlocked || alwaysUnlocked;
    }

}
