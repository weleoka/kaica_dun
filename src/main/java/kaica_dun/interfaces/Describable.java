package kaica_dun.interfaces;

/**
 * All object you might want to look at are describable. Used via User.lookAt(Describable).
 *
 * todo: add properties to describable such as state locked, unlocked. Secret things can be describe
 *  by chance and if the avatar has skill: like trap or trigger - surprises etc..
 */
public interface Describable {
    String getDescription();

    String getName();
}
