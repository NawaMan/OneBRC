// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Next trick is more low-level. "
            + "It have to do with how has map works. "
            + "We were taught that hash map uses the key map to find the value. "
            + "But in reality, the key hash can colide -- meaning that two different keys can have the same hash. "
            + "So the actual hash map only use hash to select a short list of keys before checking equality. "
            + "Therefore, the key equal check must be fast for the hash map get and set to be fast. "
            + "... and we do a lot of that in this program. "
            + "To make it fast, we make use of the new vectorize compare function. ";
    return { noteContent };
};
