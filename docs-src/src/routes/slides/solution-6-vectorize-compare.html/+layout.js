// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Next trick is more low-level. "
            + "It has to do with how has map works. "
            + "We were taught that hash map uses the hash code of the key to find the value. "
            + "But in reality, the key hashes can colide -- meaning that two different keys can have the same hash. "
            + "So the actual hash map only use hash to select a short list of keys before checking equality. "
            + "Therefore, the key equal check must be fast for the hash map get and set to be fast. "
            + "... and we do a lot of that in this program. "
            + "To make the equals check fast, we turns to the new vectorize compare function. "
            + "The good new is that it is built in. ";
    return { noteContent };
};
