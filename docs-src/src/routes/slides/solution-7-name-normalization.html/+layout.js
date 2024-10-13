// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>The last trick helps improve the comination speed.</p>"
            + "<p>"
                + "We already discussed that equal check is always need when get and put data. "
                + "We can speed this up by assigning a unique number as an ID to each name. "
            + "</p>"
            + "<p>The subsequence equal check will just need to compare the ID.</p>"
            + "<p>CLICK ON 'Add name to queue': When a new station name is detected for each chunk the name is added to the name queue.</p>"
            + "<p>CLICK ON 'Assign name ID': The name queue will try to add to the name ID map which returns the new ID if none is ready there or the existing ID if the name is ready there.</p>"
            + "<p>"
                + "CLICK ON 'ID in name equals()': "
                + "Lastly, in the StationName 'equals()', if IDs are present, the ID is then used to do equal check. "
                + "And this will happen for each key during the combine. "
                + "Since there are 1024 chunks, saving the key 'equal check' time from byte-array equals to int compare save a lot of the overall time."
            + "</p>"
            ;
    return { noteContent };
};
