// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "The trick help improve the comination speed. "
            + "We already discussed that equal check is always need when get and put data. "
            + "We can speed this up by assigning a unique number as an ID to each name. "
            + "The subsequence equal check will just need to compare the ID. ";
    return { noteContent };
};
