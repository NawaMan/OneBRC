// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The first trick is to use virtual threads to process each chunk in parallel."
            + "The map combine is also done with virtual threads.";
    return { noteContent };
};
