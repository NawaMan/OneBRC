// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "However, some time in August, I saw these two VDOs which sparked my interest."
            + "And I later found that the benchmark machine would have large memory."
            + "Which change the problem to be more CPU bound."
            + "So, I decided to give it a try.";
    return { noteContent };
};
