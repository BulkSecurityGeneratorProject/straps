/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillingPlanComponentsPage, BillingPlanDeleteDialog, BillingPlanUpdatePage } from './billing-plan.page-object';

const expect = chai.expect;

describe('BillingPlan e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let billingPlanUpdatePage: BillingPlanUpdatePage;
    let billingPlanComponentsPage: BillingPlanComponentsPage;
    let billingPlanDeleteDialog: BillingPlanDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BillingPlans', async () => {
        await navBarPage.goToEntity('billing-plan');
        billingPlanComponentsPage = new BillingPlanComponentsPage();
        expect(await billingPlanComponentsPage.getTitle()).to.eq('promalyV5App.billingPlan.home.title');
    });

    it('should load create BillingPlan page', async () => {
        await billingPlanComponentsPage.clickOnCreateButton();
        billingPlanUpdatePage = new BillingPlanUpdatePage();
        expect(await billingPlanUpdatePage.getPageTitle()).to.eq('promalyV5App.billingPlan.home.createOrEditLabel');
        await billingPlanUpdatePage.cancel();
    });

    it('should create and save BillingPlans', async () => {
        const nbButtonsBeforeCreate = await billingPlanComponentsPage.countDeleteButtons();

        await billingPlanComponentsPage.clickOnCreateButton();
        await promise.all([
            billingPlanUpdatePage.setPlanNameInput('planName'),
            billingPlanUpdatePage.setCategoryInput('5'),
            billingPlanUpdatePage.setMemberTypeInput('5'),
            billingPlanUpdatePage.setDescriptionInput('description'),
            billingPlanUpdatePage.setProrationDescInput('prorationDesc'),
            billingPlanUpdatePage.setEffectiveDateInput('2000-12-31'),
            billingPlanUpdatePage.setEffectiveStatusInput('5'),
            billingPlanUpdatePage.setAssociationInput('association'),
            billingPlanUpdatePage.setOrderTypeInput('5'),
            billingPlanUpdatePage.setAccountingBookInput('5'),
            billingPlanUpdatePage.setRatesInput('5'),
            billingPlanUpdatePage.setCurrencyInput('5'),
            billingPlanUpdatePage.setBasisInput('5'),
            billingPlanUpdatePage.setInitiationFeesInput('5'),
            billingPlanUpdatePage.setCouponsInput('coupons'),
            billingPlanUpdatePage.setGlcodeInput('glcode')
        ]);
        expect(await billingPlanUpdatePage.getPlanNameInput()).to.eq('planName');
        expect(await billingPlanUpdatePage.getCategoryInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getMemberTypeInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await billingPlanUpdatePage.getProrationDescInput()).to.eq('prorationDesc');
        expect(await billingPlanUpdatePage.getEffectiveDateInput()).to.eq('2000-12-31');
        expect(await billingPlanUpdatePage.getEffectiveStatusInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getAssociationInput()).to.eq('association');
        expect(await billingPlanUpdatePage.getOrderTypeInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getAccountingBookInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getRatesInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getCurrencyInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getBasisInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getInitiationFeesInput()).to.eq('5');
        expect(await billingPlanUpdatePage.getCouponsInput()).to.eq('coupons');
        const selectedProrated = billingPlanUpdatePage.getProratedInput();
        if (await selectedProrated.isSelected()) {
            await billingPlanUpdatePage.getProratedInput().click();
            expect(await billingPlanUpdatePage.getProratedInput().isSelected()).to.be.false;
        } else {
            await billingPlanUpdatePage.getProratedInput().click();
            expect(await billingPlanUpdatePage.getProratedInput().isSelected()).to.be.true;
        }
        expect(await billingPlanUpdatePage.getGlcodeInput()).to.eq('glcode');
        const selectedActive = billingPlanUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await billingPlanUpdatePage.getActiveInput().click();
            expect(await billingPlanUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await billingPlanUpdatePage.getActiveInput().click();
            expect(await billingPlanUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await billingPlanUpdatePage.save();
        expect(await billingPlanUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await billingPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BillingPlan', async () => {
        const nbButtonsBeforeDelete = await billingPlanComponentsPage.countDeleteButtons();
        await billingPlanComponentsPage.clickOnLastDeleteButton();

        billingPlanDeleteDialog = new BillingPlanDeleteDialog();
        expect(await billingPlanDeleteDialog.getDialogTitle()).to.eq('promalyV5App.billingPlan.delete.question');
        await billingPlanDeleteDialog.clickOnConfirmButton();

        expect(await billingPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
