// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Next we optimize the extraction of the temperature value from the string. "
            + "Instead of extract to float or double";
    return { noteContent };
};
