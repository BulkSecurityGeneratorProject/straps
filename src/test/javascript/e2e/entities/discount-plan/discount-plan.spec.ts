/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiscountPlanComponentsPage, DiscountPlanDeleteDialog, DiscountPlanUpdatePage } from './discount-plan.page-object';

const expect = chai.expect;

describe('DiscountPlan e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let discountPlanUpdatePage: DiscountPlanUpdatePage;
    let discountPlanComponentsPage: DiscountPlanComponentsPage;
    let discountPlanDeleteDialog: DiscountPlanDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DiscountPlans', async () => {
        await navBarPage.goToEntity('discount-plan');
        discountPlanComponentsPage = new DiscountPlanComponentsPage();
        expect(await discountPlanComponentsPage.getTitle()).to.eq('promalyV5App.discountPlan.home.title');
    });

    it('should load create DiscountPlan page', async () => {
        await discountPlanComponentsPage.clickOnCreateButton();
        discountPlanUpdatePage = new DiscountPlanUpdatePage();
        expect(await discountPlanUpdatePage.getPageTitle()).to.eq('promalyV5App.discountPlan.home.createOrEditLabel');
        await discountPlanUpdatePage.cancel();
    });

    it('should create and save DiscountPlans', async () => {
        const nbButtonsBeforeCreate = await discountPlanComponentsPage.countDeleteButtons();

        await discountPlanComponentsPage.clickOnCreateButton();
        await promise.all([
            discountPlanUpdatePage.setPlanIdInput('5'),
            discountPlanUpdatePage.setPlanNameInput('planName'),
            discountPlanUpdatePage.setAppliedToPlanInput('5'),
            discountPlanUpdatePage.setAppliedWithPlanInput('5'),
            discountPlanUpdatePage.setDiscountRateInput('5'),
            discountPlanUpdatePage.setDiscountUnitInput('5'),
            discountPlanUpdatePage.setMaxDiscountAmtInput('5'),
            discountPlanUpdatePage.setDescriptionInput('description')
        ]);
        expect(await discountPlanUpdatePage.getPlanIdInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getPlanNameInput()).to.eq('planName');
        expect(await discountPlanUpdatePage.getAppliedToPlanInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getAppliedWithPlanInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getDiscountRateInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getDiscountUnitInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getMaxDiscountAmtInput()).to.eq('5');
        expect(await discountPlanUpdatePage.getDescriptionInput()).to.eq('description');
        const selectedConditional = discountPlanUpdatePage.getConditionalInput();
        if (await selectedConditional.isSelected()) {
            await discountPlanUpdatePage.getConditionalInput().click();
            expect(await discountPlanUpdatePage.getConditionalInput().isSelected()).to.be.false;
        } else {
            await discountPlanUpdatePage.getConditionalInput().click();
            expect(await discountPlanUpdatePage.getConditionalInput().isSelected()).to.be.true;
        }
        await discountPlanUpdatePage.save();
        expect(await discountPlanUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await discountPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DiscountPlan', async () => {
        const nbButtonsBeforeDelete = await discountPlanComponentsPage.countDeleteButtons();
        await discountPlanComponentsPage.clickOnLastDeleteButton();

        discountPlanDeleteDialog = new DiscountPlanDeleteDialog();
        expect(await discountPlanDeleteDialog.getDialogTitle()).to.eq('promalyV5App.discountPlan.delete.question');
        await discountPlanDeleteDialog.clickOnConfirmButton();

        expect(await discountPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
