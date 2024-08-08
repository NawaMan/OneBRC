<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import CtrlBtn from './CtrlBtn.svelte';
	import { scaleMode } from '$lib/stores/scaleMode';

	function toggleScale() {
		scaleMode.update(current => !current);
	}
</script>

<style>
	.mode {
		/* functional */
		position: absolute;
		top: 0px;
		right: 0px;
		margin-right: 0.5em;
	}
</style>

<div class="mode no-print">
	<CtrlBtn text="M" hoverText="MODE" on:click={toggleScale} />
</div>
