<script lang="ts">
	import '$lib/styles/global.css';
	import '$lib/styles/note.css';
	import '$lib/styles/presentation.css';
	import '$lib/styles/tooltip.css';
	
	import Copyright      from '$lib/components/Copyright.svelte';
	import TableOfContent from '$lib/components/TableOfContent.svelte';
	import SizeMode       from '$lib/components/SizeMode.svelte';

	import { browser }    from '$app/environment';
	import { onMount }    from 'svelte';
    import { pages }      from '../../routes/pages';
	import { scaleMode }  from '$lib/stores/scaleMode';
	
	let container: HTMLElement;
	let content:   HTMLElement;

	let   isScaled    = $scaleMode;
	let   initialized = false;
	const aspectRatio = 1280 / 720;

	function adjustSize() {
		if (!container)
			return;

		if (!isScaled) {
			container.style.width   = '1280px';
			container.style.height  =  '720px';
			content.style.transform = `scale(1)`;
			content.style.transformOrigin = 'top left';
			return;
		}

		const windowWidth = window.innerWidth;
		const windowHeight = window.innerHeight;
		const windowRatio = windowWidth / windowHeight;

		if (windowRatio > aspectRatio) {
			container.style.height = 'calc(100vh - 10px)';
			container.style.width = `${Math.round(container.offsetHeight*aspectRatio)}px`;
		} else {
			container.style.width = 'calc(100vw - 10px)';
			container.style.height = `${Math.round(container.offsetWidth / aspectRatio)}px`;
		}
		let scale = (container.offsetWidth - 30) / 1280.0;
		content.style.transform = `scale(${scale})`;
		content.style.transformOrigin = 'top left';
	}

	onMount(() => {
		if (browser) {
			scaleMode.subscribe(value => {
				// Flickering fix
				// For some reason it takes quite a but of time from the statically rendered page to adjust its size.
				// So page is flickering when it's first loaded and when change pages.
				// Particularly noticeable when the page is SCALED by default but user set to FIXED.
				// This is a temporary fix to prevent flickering.
				// See in the global.css for the 'rendering' class.
				// The real solition is to have the page as a component and outter part stay the same but that would make this a SPA.

				isScaled = value;

				window.removeEventListener('resize', adjustSize);
				// TEMP: Flickering fix
				document.body.classList.add('rendering');
				adjustSize();
				// TEMP: Flickering fix
				document.body.classList.remove('rendering');
				if (isScaled) {
					window.addEventListener('resize', adjustSize);
				}
			});

			initialized = true;
		}
	});
	
</script>

<div class="container" class:scale-mode={isScaled} bind:this={container}>
	<div class="content"  bind:this={content}>
		{#if initialized}
		<slot />
		<TableOfContent {pages} />
		<SizeMode  />
		<Copyright />
		{/if}
	</div>
</div>

<style>
	.container {
		width: 1280px;
		height: 720px;
		margin: 0 auto;
		display: flex;
		flex-direction: column;
		background: #181818;
		border: 1px solid #ccc;
		position: relative;
	}
	.container.scale-mode {
		width: 100%;
		height: 100%;
		max-width: 100vw;
		max-height: 100vh;
	}
	.content {
		width: calc(1280px - 20px);
		height: calc(720px - 20px);
		min-width: calc(1280px - 20px);
		min-height: calc(720px - 20px);
		overflow: visible;
		padding: 10px;
		margin: 0px;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #C0F1FF;
		/* background: #112c63; */
		background: #181818;
		font-family: 'Noto Sans', 'Cormorant Garamond', serif;
	}

	.container:not(.scale-mode) {
		/** Bookmark : Here is where we control how much the slide shift up. */
		transform: translateY(-80px);
	}

</style>
