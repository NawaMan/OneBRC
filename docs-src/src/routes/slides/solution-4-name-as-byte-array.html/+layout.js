// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "So, if we don't use strigs, what can we use instead? "
            + "Byte array of course!";
    return { noteContent };
};
