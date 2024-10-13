// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>The first trick used to make this fast is to use virtual threads to read and process each chunk in parallel.</p>"
            + "<p>Virtual threads are also used to combine the statistic maps.</p>"
            + "<p>After some experiment, I ended up spliting the files into 1024 chunks or 32 times the CPI count.</p>";
    return { noteContent };
};
