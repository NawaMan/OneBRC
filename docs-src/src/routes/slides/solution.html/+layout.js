// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Here is the high level view of the solution. I will go over the details in the next slides."
            + "Starting from the file, we read them in to chunk. "
            + "For each chunk, the data is then extracted into a map -- a hashmap to be specific. "
            + "After that, every pair of map is combined into the larger one. "
            + "This continue until we get one final map. "
            + "Next, we sort the map and finally print it out. "
            + "Quite straightforward. ";
    return { noteContent };
};
