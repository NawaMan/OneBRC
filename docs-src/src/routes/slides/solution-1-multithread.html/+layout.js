// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Virtual threads are used to process chunk of data in parallel."
            + "Also the combine of the result map.";
    return { noteContent };
};
