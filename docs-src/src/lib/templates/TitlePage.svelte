<!-- Title page. -->
<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import CtrlBtn from '$lib/components/CtrlBtn.svelte';
	import { scaleMode } from '$lib/stores/scaleMode';
</script>

<style>
	.page {
		/* cosmetic */
		width: 100%;
		padding: auto;
		margin: auto;
	}
	.page .title {
		padding: 0px;
		margin: 0px;
		text-align: center;
		font-size: 5em;
		font-family: 'Playfair Display Bold', 'Cormorant Garamond', serif;
	}
	.page .subtitle {
		text-align: center;
		font-size: 3em;
	}
	.page .subsubtitle {
		text-align: center;
		font-size: 2em;
	}
</style>

<div class="page">
	<h1 class="title"     ><slot name="title"       /></h1>
	<p class='subtitle'   ><slot name="subtitle"    /></p>
	<p class='subsubtitle'><slot name="subsubtitle" /></p>
</div>
