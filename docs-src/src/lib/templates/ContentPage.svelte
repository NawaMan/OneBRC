<script lang="ts">
	import { getPageNavigation, type PageNavigation } from '$lib/utils/navigate';
	import { pages } from '../../routes/pages';
	import { page } from "$app/stores";
	import NavigationBar from '$lib/components/NavigationBar.svelte';

    export let title = "";
    export let subtitle = "";

	let currentPath: string | null = null;
	let navigation: PageNavigation;

	$: {
		currentPath    = $page.url.pathname.split("/").pop() || null; 
		navigation = getPageNavigation(pages, currentPath || "", "./");
	}
</script>

<div class="page">
    <h1>{title}</h1>
    <span class="subtitle">{subtitle}</span>
    <div class="content">
        <slot />
    </div>
</div>

<NavigationBar
	firstLink={navigation.first}
	prevLink={navigation.prev}
	nextLink={navigation.next}
	lastLink={navigation.last}
/>

<style>
    .page {
        /* cosmetic */
        width: 100%;
        height: 100%;
        padding-left: 2em;
        padding-right: 2em;
    }
    .page h1 {
        font-size: 2.5em;
        margin-bottom: 0em;
        font-family: 'Playfair Display Bolds', 'Cormorant Garamond', serif;
    }
    .page .subtitle {
        /* cosmetic */
        display: block;
        margin-left: 0em;
        margin-top: 0.5em;
        font-size: 1.5em;
    }
    
    .page .subtitle::after {
        content: '';
        display: block;
        margin-top: 10px; /* Adjust space after <br> */
        border-bottom: 3px solid aliceblue; /* Add a line after <br> */
    }
    .page .content {
        /* cosmetic */
        margin-top: 1em;
        margin-bottom: 2em;
        font-size: 1.5em;
        line-height: 1.5em;
        text-align: justify;
        text-justify: inter-word;
    }
</style>