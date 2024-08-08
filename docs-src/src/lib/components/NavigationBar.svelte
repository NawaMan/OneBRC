<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import CtrlBtn from './CtrlBtn.svelte';

	export let firstLink = '/';
	export let prevLink = '';
	export let nextLink = '';
	export let lastLink = '/introduction.html';

	function handleGlobalKeydown(event: KeyboardEvent) {
		if (event.key === 'ArrowLeft' && prevLink) {
			event.preventDefault();
			window.location.href = prevLink;

		} else if (event.key === 'ArrowRight' && nextLink) {
			event.preventDefault();
			window.location.href = nextLink;
		}
	}

	function playPause(event: MouseEvent) {
		alert('playPause');
	}

	onMount(() => {
		if (browser) {
			window.addEventListener('keydown', handleGlobalKeydown);
		}
	});

	onDestroy(() => {
		if (browser) {
			window.removeEventListener('keydown', handleGlobalKeydown);
		}
	});
</script>

<style>
	.nav {
		/* functional */
		position: absolute;
		bottom: 0px;
		left: 0px;
	}
</style>

<div class="nav no-print">
	<CtrlBtn text="FIRST"    on:click={() => firstLink && (window.location.href = firstLink)} isDisabled={!firstLink} />
	<CtrlBtn text="PREV"     on:click={() => prevLink  && (window.location.href = prevLink)}  isDisabled={!prevLink} />
	<CtrlBtn text="CONTINUE" on:click={playPause} />
	<CtrlBtn text="NEXT"     on:click={() => nextLink  && (window.location.href = nextLink)}  isDisabled={!nextLink} />
	<CtrlBtn text="LAST"     on:click={() => lastLink  && (window.location.href = lastLink)}  isDisabled={!lastLink}/>
</div>