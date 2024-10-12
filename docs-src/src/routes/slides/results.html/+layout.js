// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The result? Pretty good. The program run in 1.8 seconds to 3 seconds!"
            + "You can click them to see more details."
            + "The code didn't use 'unsafe' and is not using 'GraalVM'."
            + "And no hard the read code (hard to maintian)."
            + "The code is very comprehensible.";
    return { noteContent };
};
