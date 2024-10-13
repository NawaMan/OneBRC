// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>Here is the high-level view of the solution. I will go over the details in the incoming slides.</p>"
            + "<p>"
                + "Starting from the file, we read them into chunks. "
                + "For each chunk, the data is then extracted into a map -- a hashmap to be specific."
            + "</p>"
            + "<p>"
                + "The station name is used as the entry key. "
                + "The entry value contains: min, max, sum and count."
            + "</p>"
            + "<p>"
                + "After that, every pair of map is combined into the larger one. "
                + "This continues until we get one final map."
            + "</p>"
            + "<p>Next, the map is sorted by its key and finally print it out.</p>"
            + "<p>Quite straightforward. </p>";
    return { noteContent };
};
