<script lang="ts">
    import Box      from '$lib/components/Box.svelte';
    import JavaCode from '$lib/components/JavaCode.svelte';

    export let javaCode:      string;
    export let expanded:      boolean = false;
    export let revealedLines: Array<number> = [1];

    function codeCollapse() {
        expanded = false;
    }
</script>

<Box expanded={expanded} width={1000} height={650} onClick={codeCollapse}>
    <JavaCode javaCode={javaCode} revealLines={revealedLines} width="1000px" height="650px" />
</Box>