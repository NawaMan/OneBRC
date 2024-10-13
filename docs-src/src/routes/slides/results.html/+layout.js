// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The result? Pretty good, actually. The program run in between 1.8 seconds to 3 seconds! "
            + "You can click them to see more details. "
            + "The code achives this without using'unsafe' and is not using 'GraalVM'. "
            + "The code is very comprehensible.";
    return { noteContent };
};
