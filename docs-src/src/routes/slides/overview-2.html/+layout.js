// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "However, some time in August, I saw these two youtube VDOs which sparked my interest."
            + "And I later learn that the benchmark will run the machine with large memory."
            + "That changes the problem to be more CPU bound."
            + "Dispite thinking that Mr. Morling might just want some on to solve problem for him."
            + "So, I decided to give it a try.";
    return { noteContent };
};
