// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>"
                + "Next trick is more low-level. "
                + "It has to do with how has map works. "
                + "We were taught that hash map uses the hash code of the key to find the value. "
                + "But in reality, the key hashes can colide -- meaning that two different keys can have the same hash. "
                + "So the actual hash map only use hash to select a short list of keys (including one) before checking equality. "
                + "You hear that correctly -- equal check is always required to ensure the key matches. "
            + "</p>"
            + "<p>"
                + "Therefore, the key equal check must be fast for the hash map \"get-and-put\" operation to be fast. "
                + "... and we do a lot of those in this program. "
            + "</p>"
            + "<p>"
                + "To make the equals check fast, we turns to the new vectorize compare function. "
                + "The good new is that it is built in. "
            + "</p>";
    return { noteContent };
};
